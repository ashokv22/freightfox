# FreightFox Assignment

This Spring Boot project includes APIs for five tasks: <br>
1. Dynamic PDF Generation, <br> 
2. Pincode Distance, <br>
3. Weather Info for Pincode, <br>
4. Meeting Calendar Assistant, and <br>
5. Assignment-User Document Storage Service.

## Task 1: Dynamic PDF Generation

This task involves generating PDF files dynamically. The generated files will be saved in the desktop folder. A sample file is included in the project root folder for reference.

### API Endpoint
- **POST** `/api/pdf-generator/generate`

#### Request Body
- Parameters: Mentioned in the API cody

#### Response
- A dynamically generated PDF file will be saved in the desktop folder.

## Task 2: Pincode Distance

This task calculates the distance between two pincodes.

### API Endpoint
- **GET** `/api/pincode/distance?source=560011&destination=563116`

#### Parameters
- `source`: Source pincode
- `destination`: Destination pincode

#### Response
- Responses are saved in the Postman.

## Task 3: Weather Info for Pincode

This task retrieves weather information for a given pincode. OpenWeather API is used for both Geocoding and weather data fetching.

### API Endpoints
- **GET** `/api/weather/get-weather-data?pinCode=560011&weatherDate=2024-04-26`

#### Parameters
- `pincode`: Pincode for which weather information is requested
- `weatherDate`: Weather date

## Task 4: Meeting Calendar Assistant

This task involves managing meeting schedules for employees' calendars and checking for meeting conflicts.

### APIs Endpoints
- **POST** `/api/calender/meetings`
- **GET** `/api/meeting/freeSlots`
- **POST** `/api/meeting/conflicts`

#### Request Body
- Parameters: Specified in the Postman

## Task 5: Assignment-User Document Storage Service

This task involves developing a document storage service. I replied for the mail for clarification, unfortunately I didn't get any response.

### No API available

#### Clarification Requested
If clarification for this task is received, appropriate APIs will be developed accordingly.

## Postman Collection
A collection containing sample requests and responses for each API is provided for testing purposes.

### Collection Filename
`FreightFox.postman_collection.json`

## Sample Files
- `29AABBCCDD131ZD_29AABBCCDD121ZD.pdf.pdf`: A sample dynamically generated PDF file named from Gstin's.
- `sample_responses.postman_collection.json`: Sample responses for all APIs in the Postman collection.

Feel free to use and modify the APIs according to your requirements. If you encounter any issues, please feel free to reach out.