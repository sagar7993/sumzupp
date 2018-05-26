@echo off
pushd D:\
cd Work\SumzuppFiles\wkhtmltopdf\bin\
wkhtmltopdf file:///D:/Work/SumzuppFiles/assignment/%1.html %1.pdf
move %1.pdf C:\wamp64\www\pdf\%1.pdf
cd ../../assignment
exit