<template>
    <div class="container">
        <h3>Place</h3>
        <ul>
            <li><input type="checkbox"/>Trondheim</li>
            <li><input type="checkbox"/>Oslo</li>
            <li><input type="checkbox"/>Tromsø</li>
            <li><input type="checkbox"/>Stavanger</li>
            <li><input type="checkbox"/>Gjøvik</li>
            <li><input type="checkbox"/>Telemark</li>
            <li><input type="checkbox"/>Finmark</li>
        </ul>
    </div>
</template>

<style scoped>
    .container {
        display: flex;
        flex-direction: column;
        padding: 20px;
        width: 20%;
        height: 100vh;
        border-right: 2px solid black;
        align-content: center;
        overflow-y: scroll;
    }
    ul {
        list-style-type: none;
        padding: 0;
    }
    li {
        margin: 10px 0;
        font-size: 18px;
    }
    h3 {
        font-size: 24px;
        border-bottom: 2px solid black;
    }
</style>