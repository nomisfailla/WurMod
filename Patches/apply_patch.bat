@ECHO OFF

REM Lots of fun hacks ahead!

REM Save the original client source tree.
REM This is because 'git apply' will delete some files from here and we do not
REM want the user to have to keep copying them back into the client folder
REM every time that they apply patches, even though it should realisticly only
REM have to happen when they initially clone the repo.
ECHO Saving client source...
robocopy client client_saved /E /NFL /NDL /NJH /NJS /nc /ns /np

REM Rename the .git folder in the root temporarily.
REM We do this because 'git apply' acts strange when we are calling it from a
REM subdirectory of a git repository, so let's just make it thing it is not
REM in one!
ECHO Temporarily renaming .git...
PUSHD ..
attrib -h .git
REN .git .git_temp
POPD

REM Now that we have massaged the filesystem into a suitable form we can
REM actually call 'git apply'...
ECHO Applying patches...
git apply client.patch

REM Time to undo our mess...
ECHO Restoring .git...
PUSHD ..
REN .git_temp .git
attrib +h .git
POPD

ECHO Restoring client source...
RD /S /Q client
REN client_saved client

ECHO Done.
