import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CalculoPlanoAssinaturaDetailComponent } from './calculo-plano-assinatura-detail.component';

describe('CalculoPlanoAssinatura Management Detail Component', () => {
  let comp: CalculoPlanoAssinaturaDetailComponent;
  let fixture: ComponentFixture<CalculoPlanoAssinaturaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CalculoPlanoAssinaturaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CalculoPlanoAssinaturaDetailComponent,
              resolve: { calculoPlanoAssinatura: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CalculoPlanoAssinaturaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalculoPlanoAssinaturaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load calculoPlanoAssinatura on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CalculoPlanoAssinaturaDetailComponent);

      // THEN
      expect(instance.calculoPlanoAssinatura()).toEqual(expect.objectContaining({ id: 123 }));
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
