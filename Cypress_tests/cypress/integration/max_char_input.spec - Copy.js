describe('Text box with max characters', () => {
    it('displays the appropriate remaining characters count', () => {
        cy.visit('http://localhost:3000/example-3');

        cy.get('span')
            .eq(0)
            .invoke('text')
            .should('equal', '15');

        cy.get('input').eq(0).type('hello');
        cy.get('span')
            .eq(0)
            .invoke('text')
            .should('equal', '10');

        cy.get('input').eq(0).type(' my friend');
        cy.get('span')
            .eq(0)
            .invoke('text')
            .should('equal', '0');
    });


    
    // it('prevents the user from typing more characters once max is exceeded', () => {
    //     cy.visit('http://localhost:3000/example-3');

    //     cy.get('#first-name-input').type('abcdefghijklmnopqrstuvwxyz');

    //     cy.get('#first-name-input')
    //         .should('have.attr', 'value', 'abcdefghijklmno');
    // });
});