<script setup>
    import ItemFrameComponent from './ItemFrameComponent.vue';
    import { ref } from 'vue';

    let search = 'boats';

    // Byttes ut med data fra API senere
    const items = ref([
        { id: 1, image: 'src/assets/images/boat.jpg', price: '10000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 2, image: 'src/assets/images/boat.jpg', price: '20000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 3, image: 'src/assets/images/boat.jpg', price: '30000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 4, image: 'src/assets/images/boat.jpg', price: '10000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 5, image: 'src/assets/images/boat.jpg', price: '20000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 6, image: 'src/assets/images/boat.jpg', price: '30000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 7, image: 'src/assets/images/boat.jpg', price: '10000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 8, image: 'src/assets/images/boat.jpg', price: '20000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 9, image: 'src/assets/images/boat.jpg', price: '30000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 10, image: 'src/assets/images/boat.jpg', price: '10000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 11, image: 'src/assets/images/boat.jpg', price: '20000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
        { id: 12, image: 'src/assets/images/boat.jpg', price: '30000 kr', details: 'Bayliner VR 5 Cuddy OB Lite brukt' },
    ]);
</script>

<template>
    <div id="item-listing-container">
        <h2> Finding items for "{{ search }}"</h2>
        <ItemFrameComponent
            v-for="item in items"
            :id="item.id"
            :image="item.image"
            :price="item.price"
            :details="item.details"
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
</style>