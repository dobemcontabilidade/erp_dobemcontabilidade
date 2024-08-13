import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ImpostoDetailComponent } from './imposto-detail.component';

describe('Imposto Management Detail Component', () => {
  let comp: ImpostoDetailComponent;
  let fixture: ComponentFixture<ImpostoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpostoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ImpostoDetailComponent,
              resolve: { imposto: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ImpostoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpostoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load imposto on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ImpostoDetailComponent);

      // THEN
      expect(instance.imposto()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
