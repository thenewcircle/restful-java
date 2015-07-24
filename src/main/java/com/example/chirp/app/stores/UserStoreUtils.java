package com.example.chirp.app.stores;

import com.example.chirp.app.kernel.User;

public abstract class UserStoreUtils {

	public static void resetAndSeedRepository(UserStore userStore) {

		userStore.clear();

		userStore.createUser("maul", "Darth Maul");
		userStore.createUser("luke", "Luke Skywaler");

		User vader = userStore.createUser("vader", "Darth Vader");
		vader.createChirp("You have failed me for the last time.", "wars03");
		userStore.updateUser(vader);

		User yoda = userStore.createUser("yoda", "Master Yoda");
		yoda.createChirp("Do or do not. There is no try.", "wars01");
		yoda.createChirp("Fear leads to anger, anger leads to hate, and hate leads to suffering.", "wars02");
		userStore.updateUser(yoda);

		User jarJar = userStore.createUser("jarjar", "jar Jar Binks");
		jarJar.createChirp("Ooh mooey mooey I love you!");
		jarJar.createChirp("I spake!");
		jarJar.createChirp("My forgotten, da Bosses will do terrible tings to me TERRRRRIBLE is me going back der!");
		jarJar.createChirp("Hmmm... yousa point is well seen.");
		jarJar.createChirp("Wesa got a grand army. That's why you no liking us meesa thinks.");
		jarJar.createChirp("Monsters out there, leaking in here. Weesa all sinking and no power. Whena yousa thinking we are in trouble?");
		jarJar.createChirp("Better dead here than deader in the Core. Ye gods, whatta meesa sayin'?");
		jarJar.createChirp("I don't know. Mesa day startin pretty okee-day with a brisky morning munchy, then BOOM! Gettin very scared and grabbin that Jedi and POW! Mesa here! Mesa gettin' very very scared!");
		jarJar.createChirp("Yoosa should follow me now, okeeday?");
		jarJar.createChirp("How wude!");
		jarJar.createChirp("Hey yo, Daddy, Captain Tarpals. Mesa back.");
		jarJar.createChirp("Yipe! How wude!");
		jarJar.createChirp("Ya-hoo!");
		jarJar.createChirp("Yousa thinking yousa people ganna die?");
		jarJar.createChirp("Mesa called Jar-Jar Binks. Mesa your humble servant.");
		jarJar.createChirp("Where wesa goin?");
		jarJar.createChirp("Ohh, maxi big da Force. Well dat smells stinkowiff.");
		jarJar.createChirp("More? More did you spake?");
		jarJar.createChirp("Dis is nutsen");
		jarJar.createChirp("Oh Gooberfish");
		jarJar.createChirp("It's a longo taleo buta small part of it would be mesa... clumsy");
		jarJar.createChirp("Yousa might'n be sayin dat");
		jarJar.createChirp("Mesa cause one, two-y little bitty axadentes, huh? Yud say boom de gasser, den crashin der bosses heyblibber, den banished.");
		jarJar.createChirp("Hello boyos.");
		userStore.updateUser(jarJar);

	}
}
