<script setup>
    import { ref } from 'vue';
    let place = ref(false);
    let sizes = ref(false);
</script>

<template>
    <div class="container">
        <div class="category" @click="place = !place">
            <fa icon="chevron-right" v-if="!place"></fa>
            <fa icon="chevron-down" v-if="place"></fa>
            <h3>Place</h3>
        </div>
        <ul v-if="place">
            <li><input type="checkbox"/>Trondheim</li>
            <li><input type="checkbox"/>Oslo</li>
            <li><input type="checkbox"/>Tromsø</li>
            <li><input type="checkbox"/>Stavanger</li>
            <li><input type="checkbox"/>Gjøvik</li>
            <li><input type="checkbox"/>Telemark</li>
            <li><input type="checkbox"/>Finmark</li>
        </ul>
        <div class="category" @click="sizes = !sizes">
            <fa icon="chevron-right" v-if="!sizes"></fa>
            <fa icon="chevron-down" v-if="sizes"></fa>
            <h3>Sizes</h3>
        </div>
        <ul v-if="sizes">
            <li><input type="checkbox"/>XS</li>
            <li><input type="checkbox"/>S</li>
            <li><input type="checkbox"/>M</li>
            <li><input type="checkbox"/>L</li>
            <li><input type="checkbox"/>XL</li>
            <li><input type="checkbox"/>XXL</li>
        </ul>
    </div>
</template>

<style scoped>
    .container {
        display: flex;
        flex-direction: column;
        padding: 20px;
        width: 15%;
        height: 100vh;
        border-right: 2px solid black;
        align-content: center;
        overflow-y: scroll;
    }
    .category {
        display: grid;
        grid-template-columns: auto 3fr;
        align-items: baseline;
        column-gap: 3px;
    }
    ul {
        list-style-type: none;
        padding-left: 15px;
        
    }
    li {
        margin-bottom: 10px;
        font-size: 18px;
    }
    h3 {
        font-size: 24px;
        margin-bottom:0;
        border-bottom: 2px solid black;
    }
</style>