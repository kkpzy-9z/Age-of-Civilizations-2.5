copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\shader\twopointfive_fragment.glsl" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\shader\"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\sounds\coins.ogg" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\sounds\"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\Decisions.json" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\Vassals.json" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\"
copy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\Events.json" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\"
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\decisions\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\decisions\" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\languages\twopointfive\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\languages\twopointfive" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\leadersRandom\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\leadersRandom" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\game\leadersIMG\random\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\game\leadersIMG\random\" /Y /E /I
xcopy "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\UI\events\*" "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\assets\UI\events" /Y /E /I

cd "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\dj2full"
.\d2j-jar2dex.bat "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\MPD.jar" -f -o "C:\Users\knpat\IdeaProjects\AOC2 Sandbox Cut\out\artifacts\Android\classes.dex"
pause
