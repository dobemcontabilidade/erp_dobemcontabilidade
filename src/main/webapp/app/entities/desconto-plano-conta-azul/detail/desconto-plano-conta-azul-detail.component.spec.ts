import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DescontoPlanoContaAzulDetailComponent } from './desconto-plano-conta-azul-detail.component';

describe('DescontoPlanoContaAzul Management Detail Component', () => {
  let comp: DescontoPlanoContaAzulDetailComponent;
  let fixture: ComponentFixture<DescontoPlanoContaAzulDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescontoPlanoContaAzulDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DescontoPlanoContaAzulDetailComponent,
              resolve: { descontoPlanoContaAzul: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DescontoPlanoContaAzulDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DescontoPlanoContaAzulDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load descontoPlanoContaAzul on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DescontoPlanoContaAzulDetailComponent);

      // THEN
      expect(instance.descontoPlanoContaAzul()).toEqual(expect.objectContaining({ id: 123 }));
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
