#!/bin/bash
cd
cd wkhtmltox/bin
./wkhtmltopdf ../../assignment/$1_analysis.html $1.pdf
mv $1.pdf /var/www/html/pdf/$1.pdf
cd
done
