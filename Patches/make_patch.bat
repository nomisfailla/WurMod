@ECHO OFF

REM Save the autocrlf config variable.
REM I was having issues with the patch files being huge and including diffs for
REM changed line endings and I fixed it by setting 'core.autocrlf' to false.
REM However, at some point over the past 2 hours that seemed to have changed
REM and isn't necessary any more. I'll keep this code here because it could be
REM useful if I can work out what is going on.
ECHO Saving original core.autocrlf...
FOR /F "tokens=* USEBACKQ" %%F IN (`git config --get core.autocrlf`) DO (
	SET old_autocrlf=%%F
)

REM Then I came across the wonderful 'core.safecrlf' which can be set to
REM 'false' to supress all the line ending warnings.
REM So I messed with that for a little bit and now my system is in a state such
REM that I don't get the warnings and the patch is generated successfully.
REM If the patch files that you're generating are too large or your console is
REM being spammed, try messing around with these variables.

REM TODO: Work out what's happening here...
REM git config --global core.autocrlf false

REM Generate the diff.
ECHO Generating diff...
git diff --diff-filter=d --no-index client client_patched > client.patch

ECHO Restoring autocrlf...
git config --global core.autocrlf %old_autocrlf%

ECHO Done.
