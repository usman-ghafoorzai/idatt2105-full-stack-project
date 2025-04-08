<script setup>
    import ItemFrameComponent from './ItemFrameComponent.vue';
    import { useRouter } from 'vue-router';
    import { useItemStore } from '../stores/ItemStore.js';

    const itemStore = useItemStore();
    const router = useRouter();

    defineProps({
        items: {
            type: Array,
            required: true,
        },
        searchTerm: {
            type: String,
            required: true,
        },
    });

    function handleItemClick(item) {
        itemStore.setSelectedItem(item);
        router.push('/item');
    }
</script>

<template>
    <div id="item-listing-container">
        <h2> Finding items for "{{ searchTerm }}"</h2>
        <ItemFrameComponent
            v-for="item in items"
            :id="item.id"
            :image="item.image"
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
        grid-template-rows: auto auto auto;
        width: 100%;
        justify-items: center;
        align-items: center;
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