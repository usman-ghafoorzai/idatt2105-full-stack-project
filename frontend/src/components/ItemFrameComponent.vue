<script setup>
    import { ref } from "vue"
    const isBookmarked = ref(false);
    defineProps ({
        id: Number,
        image: String,
        price: String,
        details: String,
    });


    function toggleBookmark() {
        isBookmarked.value = !isBookmarked.value;
        console.log(isBookmarked.value ? "Added to bookmarks!" : "Removed from bookmarks");
    }
</script>

<template>
    <div class="item-frame">
        <div class="item-image">
            <img :src="image" alt="Item Image" />
            <div class="price-tag">{{price}} kr</div>
            <fa :icon="['far','bookmark']" class="bookmark-icon" :class="{ active: isBookmarked }" @click="toggleBookmark" v-if="!isBookmarked"/>
            <fa :icon="['fas','bookmark']" class="bookmark-icon" :class="{ active: isBookmarked }" @click="toggleBookmark" v-if="isBookmarked" style="color: yellow;"/>
        </div>
        <div class="item-details">
            {{details}}
        </div>
    </div>
</template>
<style scoped>
    .item-frame {
        display: grid;
        grid-template-rows: 4fr auto;
        width: 300px;
        height: 400px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        overflow: hidden;
        margin: 20px;
    }
    .item-image {
        position: relative;
    }
    img {
        height: 100%;
        width: 100%;
        object-fit: cover;
    }
    .item-details {
        font-size: 20px;
        width:100%;
        padding: 10px;
        box-sizing: border-box;
        white-space: normal;
        overflow-wrap: break-word;
        text-align: center;
    }
    .bookmark-icon {
        font-size: 20px;
        position: absolute;
        z-index: 10;
        top: 5px;
        right: 10px;
    }
    .bookmark-icon:hover {
        cursor: pointer;
        color: yellow;
    }
    .price-tag {
        position: absolute;
        z-index: 10;
        bottom: 0;
        width: fit-content;
        background-color: black;
        opacity: 60%;
        color: aliceblue;
        font-size: 30px;
    }

    @media (max-width: 1000px) {
        .item-frame {
            width: 250px;
            height: 350px;
        }
    }
</style>