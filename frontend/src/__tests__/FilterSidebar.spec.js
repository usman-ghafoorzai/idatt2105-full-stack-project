import { mount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import FilterSidebar from '../components/FilterSidebar.vue';

const mockCategories = {
    clothing: {
        Tops: ['T-shirts', 'Sweaters'],
        Bottoms: ['Jeans', 'Shorts'],
    },
};

describe('FilterSidebar.vue', () => {
    it('renders the component correctly', () => {
        const wrapper = mount(FilterSidebar);
        expect(wrapper.exists()).toBe(true);
    });

    it('toggles place visibility when clicked', async () => {
        const wrapper = mount(FilterSidebar);
        const placeCategory = wrapper.find('h3');
        expect(wrapper.find('ul').exists()).toBe(false);

        await placeCategory.trigger('click');
        expect(wrapper.find('ul').exists()).toBe(true);

        await placeCategory.trigger('click');
        expect(wrapper.find('ul').exists()).toBe(false);
    });

    it('toggles subcategory visibility when clicked', async () => {
        const wrapper = mount(FilterSidebar, {
            data() {
                return {
                    categories: mockCategories, // Provide mock categories
                    item: 'clothing', // Set the active category
                    visibility: { Tops: false, Bottoms: false }, // Initialize visibility
                };
            },
        });
    
        // Find the first subcategory toggle
        const subcategoryToggle = wrapper.find('h3:nth-of-type(1)');
        expect(subcategoryToggle.exists()).toBe(true); // Ensure the element exists
    
        // Check if the subcategory list is initially hidden
        expect(wrapper.find('ul').exists()).toBe(false);
    
        // Click to toggle visibility
        await subcategoryToggle.trigger('click');
        expect(wrapper.find('ul').exists()).toBe(true);
    
        // Click again to hide the subcategory list
        await subcategoryToggle.trigger('click');
        expect(wrapper.find('ul').exists()).toBe(false);
    });

    it('updates selected subcategories when checkboxes are clicked', async () => {
        const wrapper = mount(FilterSidebar, {
            data() {
                return {
                    categories: mockCategories, // Provide mock categories
                    item: 'clothing', // Set the active category
                    visibility: { Tops: true, Bottoms: true }, // Ensure subcategories are visible
                    selectedSubcategories: [], // Initialize selected subcategories
                };
            },
        });
        const subcategoryToggle = wrapper.find('h3:nth-of-type(1)');
        await subcategoryToggle.trigger('click');

        // Find the first checkbox
        const checkbox = wrapper.find('input[type="checkbox"]');
        expect(checkbox.exists()).toBe(true); // Ensure the checkbox exists
    
        // Check the checkbox
        await checkbox.setChecked();
        
        expect(wrapper.vm.selectedSubcategories).toContain('Troms og Finnmark'); // Replace with the correct value
    
        // Uncheck the checkbox
        await checkbox.setChecked(false);
        expect(wrapper.vm.selectedSubcategories).not.toContain('T-shirts'); // Replace with the correct value
    });

    it('renders price range inputs correctly', () => {
        const wrapper = mount(FilterSidebar);
        const fromPriceInput = wrapper.find('#from-price');
        const toPriceInput = wrapper.find('#to-price');

        expect(fromPriceInput.exists()).toBe(true);
        expect(toPriceInput.exists()).toBe(true);
    });

    it('updates price range inputs when values are entered', async () => {
        const wrapper = mount(FilterSidebar);
        const fromPriceInput = wrapper.find('#from-price');
        const toPriceInput = wrapper.find('#to-price');

        await fromPriceInput.setValue(100);
        await toPriceInput.setValue(500);

        expect(fromPriceInput.element.value).toBe('100');
        expect(toPriceInput.element.value).toBe('500');
    });
});