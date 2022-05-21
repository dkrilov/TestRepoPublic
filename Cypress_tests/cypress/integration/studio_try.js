describe('Text box with max characters - made  with Cypress Studio', () => {

    it('Generated', () => {
        cy.visit('http://localhost:3000/example-3');
        
        cy.get('.sc-ifAKCX')
          .should('be.visible');

        cy.get('.sc-EHOje')
          .should('be.visible');

        cy.get('[data-cy="input-first-name"]')
          .should('be.visible');

        cy.get('.sc-EHOje > :nth-child(2)')
          .should('be.visible');

        cy.get('[data-cy="first-name-chars-left-count"]')
          .should('be.visible');

        cy.get('[data-cy="input-last-name"]')
          .should('be.enabled');

        cy.get('[data-cy="last-name-chars-left-count"]')
          .should('have.attr', 'data-cy', 'last-name-chars-left-count');
          
        cy.get('[data-cy="input-first-name"]')
          .clear();

        cy.get('[data-cy="input-first-name"]')
          .type('test test');
        cy.get('[data-cy="first-name-chars-left-count"]')
          .should('have.text', '6');
        cy.get('[data-cy="first-name-chars-left-count"]')
          .invoke('text')
          .should('equal', '6');

        cy.get('[data-cy="input-last-name"]')
          .clear();

        cy.get('[data-cy="input-last-name"]')
          .type('hello Dimitry');

        cy.get('[data-cy="last-name-chars-left-count"]')
          .should('have.text', '2');
    });

});