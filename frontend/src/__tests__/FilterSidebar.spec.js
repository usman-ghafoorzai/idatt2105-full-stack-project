import { mount } from '@vue/test-utils';
import { describe, it, expect, vi } from 'vitest';
import { createTestingPinia } from '@pinia/testing';
import FilterSidebar from '../components/FilterSidebar.vue';

describe('FilterSidebar.vue', () => {
    const mockCategories = [
        { id: 'T-shirts', name: 'T-shirts' },
        { id: 'Sweaters', name: 'Sweaters' },
    ];

    vi.mock('leaflet', () => {
        return {
            default: {
                map: vi.fn(() => ({
                    setView: vi.fn().mockReturnThis(),
                    on: vi.fn(),
                    remove: vi.fn(),
                })),
                tileLayer: vi.fn(() => ({
                    addTo: vi.fn(),
                })),
                marker: vi.fn(() => ({
                    addTo: vi.fn(),
                    setLatLng: vi.fn(),
                })),
            },
        };
    });
    it('renders the component correctly', () => {
        const wrapper = mount(FilterSidebar, {
            global: {
                plugins: [createTestingPinia({ createSpy: vi.fn })],
            },
        });
        expect(wrapper.exists()).toBe(true);
    });

    it('toggles category visibility when the header is clicked', async () => {
        const wrapper = mount(FilterSidebar, {
            global: {
                plugins: [createTestingPinia({ createSpy: vi.fn })],
            },
            data() {
                return {
                    categories: mockCategories,
                    categoryVisibility: false,
                };
            },
        });

        const headers = wrapper.findAll('h3');
        const categoryHeader = headers[headers.length - 1];
        expect(categoryHeader.exists()).toBe(true);

        // Initially, the category list should not be visible
        expect(wrapper.find('ul').exists()).toBe(false);

        // Click to show the category list
        await categoryHeader.trigger('click');
        expect(wrapper.find('ul').exists()).toBe(true);

        // Click again to hide the category list
        await categoryHeader.trigger('click');
        expect(wrapper.find('ul').exists()).toBe(false);
    });

    it('renders price range inputs correctly', () => {
        const wrapper = mount(FilterSidebar, {
            global: {
                plugins: [createTestingPinia({ createSpy: vi.fn })],
            },
        });

        const fromPriceInput = wrapper.find('#from-price');
        const toPriceInput = wrapper.find('#to-price');

        expect(fromPriceInput.exists()).toBe(true);
        expect(toPriceInput.exists()).toBe(true);
    });

    it('updates price range inputs when values are entered', async () => {
        const wrapper = mount(FilterSidebar, {
            global: {
                plugins: [createTestingPinia({ createSpy: vi.fn })],
            },
        });

        const fromPriceInput = wrapper.find('#from-price');
        const toPriceInput = wrapper.find('#to-price');

        await fromPriceInput.setValue(100);
        await toPriceInput.setValue(500);

        expect(wrapper.vm.minPrice).toBe(100);
        expect(wrapper.vm.maxPrice).toBe(500);
    });
});