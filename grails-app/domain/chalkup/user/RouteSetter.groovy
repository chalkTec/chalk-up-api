package chalkup.user

import chalkup.gym.Gym

class RouteSetter {

    static mapping = {
        nickname unique: 'gym'
    }

    RouteSetter(String nickname) {
        this.nickname = nickname
    }

    Date dateCreated

    String nickname
    boolean enabled = true

    static belongsTo = [gym: Gym]

}
