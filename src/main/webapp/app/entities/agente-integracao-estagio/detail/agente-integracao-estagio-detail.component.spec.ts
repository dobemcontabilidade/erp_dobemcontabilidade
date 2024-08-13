import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AgenteIntegracaoEstagioDetailComponent } from './agente-integracao-estagio-detail.component';

describe('AgenteIntegracaoEstagio Management Detail Component', () => {
  let comp: AgenteIntegracaoEstagioDetailComponent;
  let fixture: ComponentFixture<AgenteIntegracaoEstagioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgenteIntegracaoEstagioDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AgenteIntegracaoEstagioDetailComponent,
              resolve: { agenteIntegracaoEstagio: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AgenteIntegracaoEstagioDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AgenteIntegracaoEstagioDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load agenteIntegracaoEstagio on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AgenteIntegracaoEstagioDetailComponent);

      // THEN
      expect(instance.agenteIntegracaoEstagio()).toEqual(expect.objectContaining({ id: 123 }));
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
