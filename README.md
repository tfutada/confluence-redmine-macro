# confluence-redmine-macro


Confluence macro that allows you to integrate with Redmine via [REST API].
The macro retrieves a list of issues that resides in a redmine server using REST JSON.
A jQuery *data table gadget* renders the list of JSON in a pagination table nicely.

## Usage

Just put the macro below in your confluence page.

`
{redmine}
`

## Configuration


A macro browser of Confluence allows you to specify a Redmine server url, 
what fields are visible, and how may line to be retrieved.

## Customization


This is a standard Confluence macro so you can build it like
`
# atlas-mvn package
`
And then, you can upload the generated jar into Confluence via the admin page.

## Confluence version


Version 4.x are supported.



[REST API]:http://www.redmine.org/projects/redmine/wiki/Rest_api