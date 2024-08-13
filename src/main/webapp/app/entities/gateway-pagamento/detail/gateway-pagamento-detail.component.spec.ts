import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { GatewayPagamentoDetailComponent } from './gateway-pagamento-detail.component';

describe('GatewayPagamento Management Detail Component', () => {
  let comp: GatewayPagamentoDetailComponent;
  let fixture: ComponentFixture<GatewayPagamentoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GatewayPagamentoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: GatewayPagamentoDetailComponent,
              resolve: { gatewayPagamento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(GatewayPagamentoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GatewayPagamentoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load gatewayPagamento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', GatewayPagamentoDetailComponent);

      // THEN
      expect(instance.gatewayPagamento()).toEqual(expect.objectContaining({ id: 123 }));
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
