<script setup>
    import ItemFrameComponent from './ItemFrameComponent.vue';
    import { useRouter } from 'vue-router';
    import { useItemStore } from '../stores/ItemStore.js';
    import { useSellerInformationStore } from '../stores/SellerInformationStore.js';
    import { ref, onMounted, watch } from 'vue';
    import { getFirstImage } from '../api/itemAPI.js'

    const itemStore = useItemStore();
    const sellerStore = useSellerInformationStore();
    const router = useRouter();

    const props = defineProps({
        items: {
            type: Array,
            required: true,
        },
        searchTerm: {
            type: String,
            required: true,
        },
    });

    const itemImages = ref({});


    async function fetchFirstImages() {
        const images = {};
        for (const item of props.items) {
            try {
                const image = await getFirstImage(item.id);
                images[item.id] = image;
            } catch (error) {
                console.error(`Failed to fetch the first image for item ${item.id}:`, error);
                images[item.id] = '';
            }
        }
        itemImages.value = images;
    }

    onMounted(fetchFirstImages);
    watch(() => props.items, 
          () => { 
            fetchFirstImages();
        }
    );

    function handleItemClick(item) {
        itemStore.setSelectedItem(item);
        sellerStore.setSellerId(item.seller.id);
        sellerStore.getSellerInformation();
        router.push('/item');
    }
</script>

<template>
    <div id="item-listing-container">
        <h2> Finding items for "{{ searchTerm }}"</h2>
        <ItemFrameComponent
            v-for="item in items"
            :id="item.id"
            :image="itemImages[item.id]"
            :price=item.price
            :details="item.title"
            @click="handleItemClick(item)"
        />
    </div>
</template>

<style scoped>
    #item-listing-container {
        display: grid;
        grid-template-columns: 1fr 1fr 1fr;
        grid-template-rows: 40px auto;
        width: 100%;
        justify-items: center;
        align-items: baseline;
    }
    h2 {
        text-align: center;
        margin: 20px 0;
        grid-column: 1/-1;
        grid-row: 1;
        color: grey;
    }
    @media (max-width: 1410px) {
        #item-listing-container {
            grid-template-columns: 1fr 1fr;
        }
    }
    @media (max-width: 570px) {
        #item-listing-container {
            grid-template-columns: 1fr;
        }
    }
</style>