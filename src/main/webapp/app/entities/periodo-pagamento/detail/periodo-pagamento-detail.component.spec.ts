import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PeriodoPagamentoDetailComponent } from './periodo-pagamento-detail.component';

describe('PeriodoPagamento Management Detail Component', () => {
  let comp: PeriodoPagamentoDetailComponent;
  let fixture: ComponentFixture<PeriodoPagamentoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeriodoPagamentoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PeriodoPagamentoDetailComponent,
              resolve: { periodoPagamento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PeriodoPagamentoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PeriodoPagamentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load periodoPagamento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PeriodoPagamentoDetailComponent);

      // THEN
      expect(instance.periodoPagamento()).toEqual(expect.objectContaining({ id: 123 }));
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
