import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContadorResponsavelOrdemServicoDetailComponent } from './contador-responsavel-ordem-servico-detail.component';

describe('ContadorResponsavelOrdemServico Management Detail Component', () => {
  let comp: ContadorResponsavelOrdemServicoDetailComponent;
  let fixture: ComponentFixture<ContadorResponsavelOrdemServicoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContadorResponsavelOrdemServicoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContadorResponsavelOrdemServicoDetailComponent,
              resolve: { contadorResponsavelOrdemServico: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContadorResponsavelOrdemServicoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContadorResponsavelOrdemServicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contadorResponsavelOrdemServico on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContadorResponsavelOrdemServicoDetailComponent);

      // THEN
      expect(instance.contadorResponsavelOrdemServico()).toEqual(expect.objectContaining({ id: 123 }));
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
