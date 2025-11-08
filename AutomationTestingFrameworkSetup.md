# Amazon Automation Testing Framework

## Setup Instructions
1. Install Java JDK 21
2. Install Maven
3. Download the project
4. Run `mvn clean install` to install dependencies

## Running Tests
1. Open command prompt in project directory
2. Run command: `mvn test`

## Framework Features
- Page Object Model design pattern
- TestNG test framework
- Extent Reports integration
- Multi-browser support (Chrome, Firefox, Edge)
- Headless mode capability
- Screenshot capture on test failure
- Logging implementation

## Configuration
Browser and test data configurations can be modified in:
`src/main/java/config/config.properties`

## Test Reports
After test execution, reports can be found at:
- Extent Report: `test-output/ExtentReport.html`
- Screenshots: `test-output/screenshots/`