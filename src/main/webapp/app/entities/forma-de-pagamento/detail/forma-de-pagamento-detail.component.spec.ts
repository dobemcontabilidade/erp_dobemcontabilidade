import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FormaDePagamentoDetailComponent } from './forma-de-pagamento-detail.component';

describe('FormaDePagamento Management Detail Component', () => {
  let comp: FormaDePagamentoDetailComponent;
  let fixture: ComponentFixture<FormaDePagamentoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormaDePagamentoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FormaDePagamentoDetailComponent,
              resolve: { formaDePagamento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FormaDePagamentoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormaDePagamentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load formaDePagamento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FormaDePagamentoDetailComponent);

      // THEN
      expect(instance.formaDePagamento()).toEqual(expect.objectContaining({ id: 123 }));
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
