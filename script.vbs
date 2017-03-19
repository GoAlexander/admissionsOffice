Set objArgs = WScript.Arguments
Dim args
Set args = wscript.arguments
Dim objXL
Set objXL = CreateObject("Word.Application")
With objXL
.Documents.Open (objArgs(0) + "\tmp_folder\" + objArgs(1))
.Application.Run "MergeDocuments"
End With
Set objFSO = CreateObject("Scripting.FileSystemObject")
Set objFolder = objFSO.GetFolder(objArgs(0) + "\tmp_folder")
objFolder.Delete
objXL.Documents.Open (objArgs(0) + "\files\" + objArgs(1))