<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
    images: {
        type: Array,
        required: true,
    },
});

let currentIndex = ref(0);
const currentImage = computed(() => props.images[currentIndex.value]);

function nextImage() {
    currentIndex.value = (currentIndex.value + 1) % props.images.length;
}

function prevImage() {
    currentIndex.value = (currentIndex.value - 1 + props.images.length) % props.images.length;
}
</script>

<template>
    <div class="image-gallery">
        <div class="image-container">
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
    /* endre størrelse på bildet*/
    height: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.image-container img {
    max-width: 100%; 
    max-height: 100%;
    /* skalerer bildet dersom bildet er større enn .image-container */
    object-fit: contain;
    display: block;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}


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
    z-index: 10;
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