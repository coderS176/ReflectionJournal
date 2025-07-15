# ReflectionJournal
An Online Diary with Image Support.

## Features
- User authentication and authorization
- Create, read, update, and delete journal entries
- **Image upload support for journal entries**
- Secure file storage with unique filenames
- Automatic image cleanup when entries are deleted

## Image Support
The application now supports adding images to journal entries:

### Creating an entry with image:
```
POST /journal/with-image
Content-Type: multipart/form-data

Parameters:
- title: String (required)
- content: String (required)  
- image: File (optional)
```

### Updating an entry with image:
```
PUT /journal/id:{id}/with-image
Content-Type: multipart/form-data

Parameters:
- title: String (required)
- content: String (required)
- image: File (optional)
```

### Accessing images:
```
GET /images/{filename}
```

### Configuration
- Maximum file size: 10MB
- Upload directory: `./uploads/images/`
- Supported formats: All common image formats (JPEG, PNG, GIF, etc.)

## Database Schema
The `journal_entries` table now includes an `image_url` column to store the filename of uploaded images.
