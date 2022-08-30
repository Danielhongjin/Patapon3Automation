# Patapon3Automation
This application can be used to automate flows in the psp game Patapon 3, specifically on the PPSSPP emulator.
Scripts can be built up and repeatedly run, and the application will attempt to dynamically keep the flow on track.
![image](https://user-images.githubusercontent.com/37917799/187066653-bf0e8b4b-135d-4456-9254-43c04b06faf6.png)

It can also handle simple basic patterns in gameplay, and has near-perfect beat inputs on most runspeeds due to it syncing on every sequence input.
Note that window size must be 2x. This is a visual based application, and demands exact pixel locations and unobstructed vision.

## Structure
PataponAuto launches the scripts, and currently scripts are created on demand there by the user.

ScriptBase holds actions and takes data from ScreenDataDB to ScreenIdentifier to decide what ScreenHandler to use.

ScreenHandlers do a variety of things, but they typically represent the logic for a screen's flow. For instance the hideout has a representing ScreenHandler called HideoutScreenHandler, as does Gameplay.

GameplayScreenHandler is used for the Gameplay screen, and takes a number of unique attributes in. It requires drum sequences, defined in the data.sequences package, to repeatedly enter the same pattern over and over again. It can also take iterations before it gives up.

Last but not least, classes in backend handle a number of auxiliary functions required by the application to function normally.

## Examples
If you wanted to add a new action, like going to another mission, you would need to define the flow to get to that mission. In this case, the implementation flow would be:

1. Create an action in types/Action.java to be used by each ScriptBase's action store.

2. Define the logic in each ScreenHandler you think will need to know how to get there.
     e.g. HideoutScreenHandler -> MissionSelectScreenHandler. The rest should take care of itself.

3. Finally, consider the action flow to be fed to the ScriptBase. You can't just climb a mountain, you have to learn how to get there first. The flow should look something like this:
     e.g. TOHOME -> TOMISSIONSELECT -> TOSOMEMISSION -> TOGAMEPLAY.
     This is a logical structure for how a script may be defined.

4. Also consider what your objective would be on this mission, and what the proper drum sequence would look like.          Unfortunately GameplayScreenHandler is not dynamic and performs a repeated pattern, so plan accordingly.

## Setup
It's been a while since I setup eclipse, so I can't be very helpful on this one.

1. Install eclipse.
2. Install java.
3. Download project.
4. Import project via trial and error.
5. Install ppsspp
6. Set frameskip to auto
7. Modify applicationName in PataponAuto to be the same as the ppsspp window when it's running Patapon 3.
7. Profit.

## Notes
1. applicationName MUST be the same. If it's not then the application will not be able to find it.
2. Frameskip auto helps to keep the gamespeed constant, it is highly recommended to keep this setting active during use.
3. runSpeed in PataponAuto must be the same as what alternative speed you're currently running. For instance, if you have your alternative speed set to 275 runSpeed must be 2.75.
4. "Sell" type actions can be put in a row and will be combined into a single sell action. It does not care about the level of the items it's selling and may have some unidentified margin of error, so beware. I did not have a large test pool.
5. Why are you still reading this
6. Below is an image of where to find window size. If the application is messing up, deselect 2x and then reselect it to ensure it is truly in 2x mode.

![image](https://user-images.githubusercontent.com/37917799/187067561-aaf06082-8568-45c3-bde3-dfe06dfd6e4e.png)
