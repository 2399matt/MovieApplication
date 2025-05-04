# Movie Tracker App

## Project Goal
Build a full-stack web application where users can search for movies, manage a personal watchlist, use forums to create post / replies, and integrate an AI bot.

---

## Overview

- Search for movies using the OMdb api
- Save or remove movies from their personal watchlist
- Mark movies as watched or unwatched
- forums with posts / replies / upvote system
- AI chatbot using Ollama and websockets

---

**Backend:**
- Java 
- Spring Boot

**Frontend:**
- Thymeleaf 
- HTMX 
- Bootstrap 5
- CSS (Inline / main.css is AI generated, minimal styling).

**Database:**
- MySQL 

**Real-Time:**
- WebSocket with SockJS
- Ollama AI chatbot 

---

## Authentication

- login / registration secured with Spring Security (JDBCUserDetailsManager)
- Passwords encrypted with BCrypt
- Role-based 
- Validation on registration form

---

## Database Design

- Users have one-to-many relationships with:
  - Movies (personal movie list)
  - Posts (forum posts)
  - Replies (forum replies)
- Top 250 IMDb movies stored separately in a top_movies table for browsing
- Query optimizations 

---

## AI Chatbot

- Use of Ollama 3.1:8b (for now)
- Each user has their own subscription

---

## Forum Features

- Post creation with title/content and user association
- dynamic page with htmx
- Upvote system 

---


## Future Improvements

- Add pagination to forum and initial search results
- Add a chathistory feature for the AI chatbot
- Add specific exception handling, unit tests, and oAuth

---

## Getting Started

1. Clone the repository
2. Set up a local MySQL database and configure `application.properties`
3. Insert OMDb API key
4. Run with IDE

---