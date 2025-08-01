cd C:\Program Files (x86)\Launch4j
launch4jc.exe "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\create.xml"

copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\shader\twopointfive_fragment.glsl" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game\shader"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\sounds\coins.ogg" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\sounds"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\Vassals.json" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\Decisions.json" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\Events.json" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game"
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\decisions\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game\decisions\" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\languages\twopointfive\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game\languages\twopointfive\" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\leadersRandom\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game\leadersRandom\" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\leadersIMG\random\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\game\leadersIMG\random" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\UI\events\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\UI\events\" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\jre\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable\jre\" /Y /E /I
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\AoC2point5.jar" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Launchers\executable"

pause
