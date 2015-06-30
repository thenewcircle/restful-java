package com.example.chirp.kernel.stores;

import com.example.chirp.kernel.User;

public abstract class UsersStoreUtils {

  public static void resetAndSeedRepository(UsersStore userStore) {

    userStore.clear();

    userStore.createUser("maul", "Darth Maul");
    userStore.createUser("luke", "Luke Skywaler");

    User vader = userStore.createUser("vader", "Darth Vader");
    vader.createChirp("You have failed me for the last time.", "wars03");
    userStore.updateUser(vader);

    User yoda = userStore.createUser("yoda", "Master Yoda");
    yoda.createChirp("Do or do not.  There is no try.", "wars01");
    yoda.createChirp("Fear leads to anger, anger leads to hate, and hate leads to suffering.", "wars02");
    userStore.updateUser(yoda);

  }
}
