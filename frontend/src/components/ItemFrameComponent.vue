<script setup>
import { ref, onMounted } from "vue";
import { bookmarkItem, deleteBookmark } from '../api/bookmarkAPI.js';
import { getLoggedInUser } from '../api/userAPI.js';

const isBookmarked = ref(false);
const user = ref(null);

const props = defineProps({
  id: Number,
  image: String,
  price: Number,
  details: String,
});

onMounted(async () => {
  try {
    // Get logged-in user when component mounts
    user.value = await getLoggedInUser();
    // Could add code here to check if this item is already bookmarked
  } catch (error) {
    console.error("Not logged in or error fetching user");
  }
});

async function toggleBookmark(event) {
  // Prevent the item click event from triggering
  event.stopPropagation();

  try {
    // Try to get logged in user if not already available
    if (!user.value) {
      user.value = await getLoggedInUser();
    }

    if (isBookmarked.value) {
      await deleteBookmark(user.value.id, props.id);
      console.log("Removed from bookmarks");
    } else {
      await bookmarkItem(user.value.id, props.id);
      console.log("Added to bookmarks!");
    }

    isBookmarked.value = !isBookmarked.value;
  } catch (error) {
    console.error("Error with bookmark:", error.message);
    alert("Please log in to bookmark items");
  }
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
        grid-template-rows: 3fr auto;
        width: 300px;
        height: 400px;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        overflow: hidden;
        margin: 20px;
    }
    .item-image {
        position: relative;
        height: 100%;
        overflow: hidden;
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
