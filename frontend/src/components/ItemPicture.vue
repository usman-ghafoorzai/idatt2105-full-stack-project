<script setup>
import { ref, computed } from 'vue';
import { getItemImage } from '../api/itemAPI.js';

const props = defineProps({
    images: {
        type: Array,
        required: true,
    },
    itemId: {
        type: Number,
        required: true,
    },
});

let currentIndex = ref(0);
const currentImage = computed(() => props.images[currentIndex.value]);

async function nextImage() {
    const nextIndex = currentIndex.value + 1;
    if (nextIndex >= props.images.length) {
        try {
            const nextImage = await getItemImage(props.itemId, 7);
            props.images.push(nextImage);
        } catch (error) {
            console.error('Error fetching next image:', error);
        }
    }
    currentIndex.value = (currentIndex.value + 1) % props.images.length;
}

function prevImage() {
    currentIndex.value = (currentIndex.value - 1 + props.images.length) % props.images.length;
}
</script>

<template>
    <div class="image-gallery">
        <div 
        class="image-container"
        :style="{ backgroundImage: `url(${currentImage})`}">
            <img :src="currentImage" alt="Gallery Image" />
            <button class="prev" @click="prevImage"><</button>
            <button class="next" @click="nextImage">></button>
        </div>
    </div>
</template>

<style scoped>
.image-gallery {
    position: relative;
    width: 100%;
    overflow: hidden;
}

.image-container {
    position: relative;
    width: 100%;
    height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
}

.image-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: center; 
    background-repeat: no-repeat; 
    background-image: inherit; 
    filter: blur(10px); 
    z-index: 1;
}

/* Actual image */
.image-container img {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain; /* Maintain aspect ratio */
    display: block;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    position: relative;
    z-index: 2; /* Place the image above the blurred background */
}

/* Navigation buttons */
.prev, .next {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    font-size: 30px;
    color: white;
    background: rgba(0, 0, 0, 0.5);
    border: none;
    cursor: pointer;
    padding: 10px;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    z-index: 3; /* Ensure buttons are above the image */
    opacity: 0;
    transition: opacity 0.3s ease;
}

.prev:hover, .next:hover {
    background: rgba(0, 0, 0, 0.8);
}

.prev {
    left: 10px;
}

.next {
    right: 10px;
}

.image-container:hover .prev,
.image-container:hover .next {
    opacity: 1;
}
</style>