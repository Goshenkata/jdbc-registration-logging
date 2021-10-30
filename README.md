Just me toying around with jdbc, pretty cool stuff tbh.

CONFIGURATION:
The project works on localhost so for it to work you need to set the username and password string in the Database class, 
it has a provided mysqldump for creating the database

INFO:
The program registers and logs users in the *user* table 
it tracks contains ou theoritical user's usernames , first and last names, emails,
passwords as a simple varchar (very secure)
date of birth and the profile picture
the table has the following definition:

+-----------+-------------+------+-----+---------+----------------+

| Field     | Type        | Null | Key | Default | Extra          |


| userID    | int         | NO   | PRI | NULL    | auto_increment |

| username  | varchar(30) | NO   | UNI | NULL    |                |

| firstname | varchar(30) | NO   |     | NULL    |                |

| lastname  | varchar(30) | NO   |     | NULL    |                |

| email     | varchar(30) | NO   |     | NULL    |                |

| password  | varchar(30) | NO   |     | NULL    |                |

| birthday  | date        | NO   |     | NULL    |                |

| pfp       | mediumblob  | YES  |     | NULL    |                |

+-----------+-------------+------+-----+---------+----------------+
	
It allows registration and logging with input checking, GUI made with JavaFX
