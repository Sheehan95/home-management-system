CONTENTS OF THIS FILE
----------------------

* Introduction
* Requirements
* Installation
* Configuration



INTRODUCTION
----------------------

This folder contains the necessary .php files to create a database, create a table and insert values into it
and also to view the items inserted into it.

It retrieves the values from a web.py server located on a Raspberry Pi. It then guides you through a step-by-step
path to get the values from that onto a table.



REQUIREMENTS
----------------------

You require the following for this to work:

* XAMPP installed onto your PC
* A Raspberry Pi
* The web.py server running on the Pi


INSTALLATION
----------------------

* Download XAMPP and follow the instructions to install:
	https://www.apachefriends.org/download.html

* Included is the web.py code that was used to generate the values. Copy the code into the Raspberry Pi naming it
{filename}.py . Then in the terminal type: sudo easy_install web.py, after that simply type: python {filename}.py.
This will run the server on the Raspberry Pi. To view it enter in the IP of the Pi itself, to see this type:
hostname -I into the terminal.

* Next insert the requests folder into the htdocs folder, (Default location: C:\xampp\htdocs).

* Run XAMPP, start Apache and MySQL. There may be some configuring to do, it'll be covered below. But if everything
runs go to your browser and enter: "https://localhost:{portnumber}/requests". The port number is whatever you decided
in the configuration.


CONFIGURATION
----------------------

* You will have, more then likely, have to edit the following config files on XAMPP:
	- Apache: httpd.conf	and 	httpd-ssl.conf
	- MySQL:  my.ini
If there is no need to edit these files then everything should have started first time. You can manually set your
own ports or you can swap out the config files to the ones I have supplied. Simply swap them out at their location
in the XAMPP folder.