package com.epiuse.labs.epifoos.security

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.epiuse.labs.epifoos.player.Player
import com.epiuse.labs.epifoos.player.PlayerElo
import com.epiuse.labs.epifoos.player.PlayerSummary
import com.epiuse.labs.epifoos.security.cognito.CognitoService
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import java.time.Instant

object SecurityService {

    fun signIn(signInRequest: SignInRequest): DecodedJWT {
        val signInResult = CognitoService.signIn(signInRequest)

        return JWT.decode(signInResult.accessToken)
    }

    fun signUp(signUpRequest: SignUpRequest): PlayerSignUpResult {
        try {
            CognitoService.signUp(signUpRequest)

            var newPlayer: Player?
            val playerSummary = transaction {
                newPlayer = Player.new(signUpRequest.email) {
                    this.username = signUpRequest.username
                    this.firstName = signUpRequest.firstName
                    this.lastName = signUpRequest.lastName
                }

                PlayerElo.new {
                    this.player = newPlayer!!
                    this.capturedDate = Instant.now()
                    this.change = 0.0
                    this.elo = STARTING_ELO
                }

                newPlayer!!.toSummary()
            }
            return PlayerSignUpResult.Success(playerSummary)
        } catch (e: Exception) {
            return PlayerSignUpResult.Failure("Player sign up failed due to an unknown error")
        }
    }

    sealed class PlayerSignUpResult {

        data class Success(val player: PlayerSummary) : PlayerSignUpResult()

        data class Failure(val message: String) : PlayerSignUpResult()
    }

    fun confirmSignUp(confirmSignUpRequest: ConfirmSignUpRequest) {
        CognitoService.confirmSignUp(confirmSignUpRequest)
    }

    private const val STARTING_ELO = 1000.0
}