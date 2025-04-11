import apiClient from "./apiClient";

// Function to get the logged-in user's data
export async function getLoggedInUser() {
  try {
    const response = await apiClient.get("/users/me");
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to fetch user data";
    throw new Error(errorMessage);
  }
}

// Function to upload a new profile picture
export async function uploadProfilePicture(image, userId) {
  const formData = new FormData();
  formData.append('image', image);

  try {
    const response = await apiClient.post(`/users/${userId}/image`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data', // Set content type to multipart/form-data for file upload
      },
    });
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Image upload failed';
    throw new Error(errorMessage);
  }
}

// Function to fetch the user's profile image
export async function getUserImage(userId) {
  try {
    const response = await apiClient.get(`/users/${userId}/image`, {
      responseType: 'blob', // Ensure Axios treats the response as binary data
    });

    if (response.data instanceof Blob) {
      console.log('Image Blob received:', response.data);

      // Convert the Blob to a Data URL using FileReader
      const reader = new FileReader();
      

      return new Promise((resolve, reject) => {
        reader.onloadend = () => {
          resolve(reader.result);
        };
        reader.onerror = reject;

        // Read the Blob as a Data URL
        reader.readAsDataURL(response.data);
      });
    } else {
      throw new Error('Received response is not a valid Blob.');
    }
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to fetch user image';
    console.error('Error fetching user image:', errorMessage);
    throw new Error(errorMessage);
  }
}

// Function to update a user with any combination of fields
export async function updateUser(userId, updatedFields) {
  try {
    const response = await apiClient.put(`/users/${userId}`, updatedFields);
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || 'Failed to update user';
    throw new Error(errorMessage);
  }
}


export async function getSellerInformation(sellerId) {
  try {
    const response = await apiClient.get(`/users/${sellerId}`);

    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to fetch seller information";
    throw new Error(errorMessage);
  }
} 
