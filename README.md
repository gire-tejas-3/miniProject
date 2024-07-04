# Ecommerce Application - Mini Project
## Getting Started.

### Prerequisites 

1.  Java Development Kit 8 or higher.
2.  MySQL
3.  Git

### Note 
1.  Database Configuration stored in "config.properties" in src/.. root Directory.
2.  Find the jdbc Driver in driver folder.
3.  Find SQL queries in sql folder.

## Git Commands

###  1. Clone the Project
Clone the project repository using the following command:
```bash
git clone git@github.com:gire-tejas-3/miniProject.git
```

###  2. Create and Switch to a New Branch
```bash
git branch branchName
git chekout branchName
```
or
```bash
git checkout -b branchName
```

###  3. Check Git Status
To check the status of tracked and untracked files:
```bash
git status
```

###  4. Add Files to Staging Area
To add all untracked and modified files:
```bash
git add .
```

###  5. Commit Changes
To commit the changes in the working tree:
```bash
git commit -m "Commit Message"
```

###  6. Push Changes to Remote Repository
To push changes to the remote repository:
```bash
git push origin branchName
```

## Configure Properties File
Update the database connection settings in your project
```bash
db.url=jdbc:mysql://localhost:3306/project_db
db.user=root
db.password=password
```


