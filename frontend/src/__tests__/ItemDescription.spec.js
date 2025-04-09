import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import ItemDescription from '../components/ItemDescription.vue';

describe('ItemDescription.vue', () => {
    const mockItem = {
        title: 'Test item',
        price: 3220,
        description: 'Test description',
        location: 'Nidarosdomen, Trondheim',
        categories: [
            'Electronics',
            'Computers',
            'Laptops'
        ]
    };

    it('renders the item title', () => {
        const wrapper = mount(ItemDescription, {
            props: mockItem,
        });

        expect(wrapper.find('h1').text()).toContain(mockItem.title);
    });

    it('renders the item price', () => {
        const wrapper = mount(ItemDescription, {
            props: mockItem,
        });

        expect(wrapper.find('h2').text()).toContain(mockItem.price.toString() + ' kr');
    });

    it('renders the item description', () => {
        const wrapper = mount(ItemDescription, {
            props: mockItem,
        });

        expect(wrapper.find('#description').text()).toContain(mockItem.description);
    });

    it('renders the item location', () => {
        const wrapper = mount(ItemDescription, {
            props: mockItem,
        });

        expect(wrapper.find('#location').text()).toContain(mockItem.location);
    });

    it('renders the categories', () => {
        const wrapper = mount(ItemDescription, {
            props: mockItem,
        });

        expect(wrapper.find('#category:nth-of-type(1)').text()).toContain(mockItem.categories[0]);
        expect(wrapper.find('#category:nth-of-type(2)').text()).toContain(mockItem.categories[1]);
        expect(wrapper.find('#category:nth-of-type(3)').text()).toContain(mockItem.categories[2]);
    })

});