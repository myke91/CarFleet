1. Describe possible performance optimizations for your Code.
 - The current car list api has quiet a number of fields and the difference in fields with the car
 details api is just 3 (in terms of numbers). So the car list api can contain all the fields
 necessary to show in the detail view so the second network call is not done. Alternatively, the car list
 api should have only the relevant fields i.e. title, lat and lon and then we can have the car detail
 api to retrieve the additional fields

 -


2. Which things could be done better, than you’ve done it?
   - There no option granted for user to not allow location permission and continue using the app with limited functionality.
   - Authorization bearer token is hardcoded in the repository since it's provided a one time use. A
   better approach will be, after it's received from the backend its stored in sharedpreferences.

3. MVP/MVI/MVVM pattern
   - The pattern used in this project is MVVM. Given that, it ties in nicely with coroutines (IMO). Also, it is Google's
   recommended architecture for android applications. There are concerns about MVVM not being appropriate for very
   large applications but that is not a concern here since this is a very small application.
