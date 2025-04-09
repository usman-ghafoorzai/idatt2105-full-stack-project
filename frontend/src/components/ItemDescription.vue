<script setup>
    import { ref } from 'vue';

    let c = ref(0);
    defineProps({
        title: String,
        price: Number,
        description: String,
        location: String,
        categories: Array
    })

    function randomColor() {
        const colors = ["darksalmon", "aquamarine", "lightgreen", "yellow", "teal", "honeydew", "mediumpurple"];
        return colors[Math.floor(Math.random() * colors.length)];
    }
</script>


<template>
    <div id="item-description-container">
        <h1> {{ title }} </h1>
        <h2>{{ price }} kr</h2>
        <hr>
        <div id="categories">
            <div id="category" v-for="category in categories" :style="{backgroundColor: randomColor()}">
                 {{ category }}
            </div>
        </div>
        <div id="location"> {{ location }} </div>
        <div id="description"> {{ description }} </div>
    </div>
</template>

<style scoped>
    #item-description-container {
        display: flex;
        flex-direction: column;
        border: 1px solid black;
        align-items: center;
        justify-content: center;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        border-radius: 10px;
        width: 100%;
        gap: 15px;
    }
    hr {
        width: 90%;
        border: 1px solid gray;
    }
    h2 {
        width: 100%;
        align-content: center;
        margin-bottom: 0;
        margin-left: 10%
    }
    #categories {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
    #category {
        font-size: 20px;
        border-radius: 12px;
        padding: 5px 10px;
        margin: 5px;
    }
    #location {
        width: 80%;
        background-color: #E5EFE1;
        border-radius: 12px;
        padding: 5px 10px;
    }
    #description {
        height: 250px;
        margin: 5% 15%;
        overflow-y: scroll;
    }
    @media (max-width: 900px) {
        #item-description-container {
            height: auto;
            padding: 20px;
        }
    }
</style>