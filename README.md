# Twitter-Project

> Twitter feed API that allows users to view timelines, like/comment/share tweets with friends and communicate with others.

---
## Section
- [API Requests](#api-requests)
- [Query parameter](query-parameter)
- [GET](#get)
- [DELETE](#delete)
- [POST](#post)
- [Timeline](#timeline)


##  Use Cases and Constraints Outline

- User posts a tweet
- Service pushes tweets to followers, sending push notifications and emails
- User views the user timeline (activity from the user)
- User views the home timeline (activity from people the user is following)
  


---

 ## API Requests

>To make a REST API request, combine the HTTP GET, POST, PUT, PATCH, or DELETE method, the URL to the API service, 
the URI to a resource to query, submit data to, update, or delete, and one or more HTTP request headers.

---

# Basic Query parameter 

### GET

**tweets request parameters**

Type | Name|Description|
| --- | ---|---|
|int | user_ids|The numerical ID of the desired user.|


---
### Get Example 
> Getting a user instance from database/redis with given id
```java
{
  @GetMapping("/customer/{id}")
    public CustomerUI getUserById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
            CustomerUI c = service.getUserById(id);

            return c;

    }
}
```
  >**If the returned response object contains an id and the text of the Tweet, then successfully created a Tweet.**

---

### DELETE
>Deletes a user from current database and redis.

```java
{
   @DeleteMapping("/customer/{id}")
        public String deleteUser (@PathVariable(value = "id") long id) throws ResourceNotFoundException {

            return service.deleteUserById(id);

        }
}

```
>Destroys the status specified by the required ID parameter. The authenticating user must be the author of the specified status. Returns the destroyed status if successful.
---

### POST 
>creates a user with given name, stores into 
database and redis.

|Type | Name| Description|
| --- | ---| ---|
| String| string|The name of the user to create.|


***Example Request***
```java
@PostMapping("/customer")
    public String createUser(String name) {

       return service.createUser(name);

    }
```

### PUT 
**statuses/update**
>Updates the authenticating user's current name, with the given user id and updated new name.

```java

  @PutMapping("customer/{id}")
        public CustomerUI updateUser(@PathVariable(value= "id")long id, String name) throws Exception {
                return service.updateUser(id, name);
        }
```


## Entity Relational Diagram
![diagram](https://user-images.githubusercontent.com/76961998/156243936-9151954a-93fc-4633-9260-36da7303039a.png)

>The diagram shows the relationships of entity sets stored in the twitter database.
---


## Timeline 

- Tweets are delivered in reverse-chronological order, starting with the most recent. 
  
- Results are paginated up to 100 Tweets per page.
  
-  Pagination tokens are provided for paging through large sets of Tweets.
   
-  The Tweet IDs of the newest and the oldest Tweets included in the given page.

***Example***

```java

  @GetMapping("/tweet")
    public List<Tweet> getTimeline(long user_id) throws ResourceNotFoundException {

        boolean popular = userService.isPopular(user_id);
        List<Tweet> timeline = new ArrayList<>();

        if (popular) {
            timeline = tweetService.getFeed(user_id);
            return timeline;
        } else {
            timeline = tweetService.findAll();
        }

        return timeline;
    }

```


## Constraints and assumptions

General

 - Traffic is not evenly distributed

 - Posting a tweet should be fast

 - Fanning out a tweet to all of the followers should be fast, unless user have millions of followers

 - 100 million active users

 - 500 million tweets per day or 15 billion tweets per month

 - Each tweet averages a fanout of 10 deliveries

 - 5 billion total tweets delivered on fanout per day

 - 150 billion tweets delivered on fanout per month

 - 250 billion read requests per month

 - 10 billion searches per month


## Tweet

 - Store the user's own tweets to populate the user timeline in database
 - Delivering tweets and building the home timeline
 - The Client posts a tweet to the Web Server
 - The Web Server forwards the request to the Write API server
 - The Write API stores the tweet in the user's timeline on a SQL databas
 
## Use case: User views the home timeline
- The Client posts a home timeline request to the Web Server
- The Web Server forwards the request to the Read API server

- The Read API server contacts the Timeline Service, which does the following:

  Gets the timeline data stored in the Memory Cache, containing tweet ids and user ids - O(1)
 
  Queries the Tweet Info Service with a multiget to obtain additional info about the tweet ids - O(n)
 
  Queries the User Info Service with a multiget to obtain additional info about the user ids - O(n)
  
## Use case: User views the user timeline

- The Client posts a user timeline request to the Web Server
- The Web Server forwards the request to the Read API server
- The Read API retrieves the user timeline from the SQL Database

## Use case: User searches keywords
- The Client sends a search request to the Web Server
- The Web Server forwards the request to the Search API server
- The Search API contacts the Search Service


## Scale:
waiting to be edied.

## Caching
>Achieved CRUD caching utilities with jedis resource pool.

```java
 public Object hget(byte[] key, byte[] field) {
        Jedis jedis = null;
        Object res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.hget(key, field);
        } catch (Exception e) {

            log.error(e.getMessage());
        } finally {
            returnResource(jedisPool, jedis);
        }

        return res;
    }

```

## Asynchronism and microservices
- User Services
- Tweet Services
- Follower Services
 

