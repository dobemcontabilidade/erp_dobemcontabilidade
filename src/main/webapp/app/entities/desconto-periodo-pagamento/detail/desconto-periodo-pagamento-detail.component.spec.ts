import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DescontoPeriodoPagamentoDetailComponent } from './desconto-periodo-pagamento-detail.component';

describe('DescontoPeriodoPagamento Management Detail Component', () => {
  let comp: DescontoPeriodoPagamentoDetailComponent;
  let fixture: ComponentFixture<DescontoPeriodoPagamentoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescontoPeriodoPagamentoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DescontoPeriodoPagamentoDetailComponent,
              resolve: { descontoPeriodoPagamento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DescontoPeriodoPagamentoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DescontoPeriodoPagamentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load descontoPeriodoPagamento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DescontoPeriodoPagamentoDetailComponent);

      // THEN
      expect(instance.descontoPeriodoPagamento()).toEqual(expect.objectContaining({ id: 123 }));
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
