/// <reference types="Cypress" />

describe('Text box with max characters', () => {
    it('displays the appropriate remaining characters count', () => {
        cy.visit('http://localhost:3000/example-2');

        cy.get('span')
            // .invoke('text')
            // .should('equal', '15')
            .should('have.text', '15');

        cy.get('input').type('hello');
        cy.get('input').clear();
        cy.get('input').type('This is {enter}');

        cy.get('span')
            .invoke('text')
            .should('equal', '7');

        cy.get('input').type(' my friend');

        cy.get('span')
            .invoke('text')
            .should('equal', '0');
    });

    it('prevents the user from typing more characters once max is exceeded', () => {
        cy.visit('http://localhost:3000/example-2');

        cy.get('input#main-input').type('abcdefghijklmnopqrstuvwxyz');

        cy.get('input#main-input')
            .should('have.attr', 'value', 'abcdefghijklmno');
    });
});