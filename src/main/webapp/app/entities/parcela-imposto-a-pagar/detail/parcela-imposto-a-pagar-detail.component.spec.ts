import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ParcelaImpostoAPagarDetailComponent } from './parcela-imposto-a-pagar-detail.component';

describe('ParcelaImpostoAPagar Management Detail Component', () => {
  let comp: ParcelaImpostoAPagarDetailComponent;
  let fixture: ComponentFixture<ParcelaImpostoAPagarDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParcelaImpostoAPagarDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ParcelaImpostoAPagarDetailComponent,
              resolve: { parcelaImpostoAPagar: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ParcelaImpostoAPagarDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParcelaImpostoAPagarDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load parcelaImpostoAPagar on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ParcelaImpostoAPagarDetailComponent);

      // THEN
      expect(instance.parcelaImpostoAPagar()).toEqual(expect.objectContaining({ id: 123 }));
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
