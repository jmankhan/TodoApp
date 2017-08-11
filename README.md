# Pre-work - *TodoApp*

**TodApp** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Jalal Khan**

Time spent: **2** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds


## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://imgur.com/IrBDidY.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Android is a great platform for building really cool, modern apps that a lot of people are interested in. Viewing it as a frontend to an enterprise application helps me reason about lots of logic that can motivate good design practices, but it is important to keep in mind that it is a powerful, versatile platform for creating standalone applications as well.  

I would like to experiment with more design patterns for presenting data logically and consistently with web applications for a more streamlined user experience that still gives a mobile, Android feel. Although this is more of a design concern, it can help inform architectural, technical decisions as well.


**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** Android is architectured on an MVC system, so understanding how each component functions in the system goes a long to understanding what each piece of code does. The ListView is the presentation, the ArrayAdapter is the controller, and the ArrayAdapter type (TodoListItem in my case) is the model. This explains that ArrayAdapter is in charge of updating the presentation layer when a data change occurs, and must do so in a safe, fast way. convertView helps us do that by finding which element of the ListView ought to be updated, and leaving the rest of the list alone. 

## Notes

Finding the "best" way to do any one thing can be impossible since there are many different (and good) paradigms one can follow. Evaluating different approaches to a problem in context is a difficult, learned skill, but was invaluable even in this simple application.

## License

    Copyright 2017 Jalal Khan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.