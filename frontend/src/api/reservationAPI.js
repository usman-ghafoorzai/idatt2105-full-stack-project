import apiClient from "./apiClient";

// Get all reserved items for a specific user
export async function getReservedItems(userId) {
  try {
    const response = await apiClient.get(`/reservations/${userId}`);
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to fetch reserved items";
    throw new Error(errorMessage);
  }
}

// Reserve an item for a user
export async function reserveItem(userId, itemId) {
  try {
    const response = await apiClient.post(`/reservations/${userId}/${itemId}`);
    return response.data;
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to reserve item";
    throw new Error(errorMessage);
  }
}

// Delete a reservation for a user and item
export async function deleteReservation(userId, itemId) {
  try {
    await apiClient.delete(`/reservations/${userId}/${itemId}`);
  } catch (error) {
    const errorMessage = error.response?.data?.message || "Failed to delete reservation";
    throw new Error(errorMessage);
  }
}
