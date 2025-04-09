import axios from 'axios';


export async function getAddress(lat, lng) {
    lat = lat.toString();
    lng = lng.toString();
    const response = await axios.get(
        // Gratis API for reverse geocoding, men grunnet derfor har vi ingen garanti for at den alltid fungerer
        `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`
    );
    if (response.data && response.data.display_name) {
        return response.data.display_name;
    } else {
        console.error('No address found');
    }
}