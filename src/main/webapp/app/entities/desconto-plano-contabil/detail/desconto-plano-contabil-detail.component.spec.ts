import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DescontoPlanoContabilDetailComponent } from './desconto-plano-contabil-detail.component';

describe('DescontoPlanoContabil Management Detail Component', () => {
  let comp: DescontoPlanoContabilDetailComponent;
  let fixture: ComponentFixture<DescontoPlanoContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescontoPlanoContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DescontoPlanoContabilDetailComponent,
              resolve: { descontoPlanoContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DescontoPlanoContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DescontoPlanoContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load descontoPlanoContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DescontoPlanoContabilDetailComponent);

      // THEN
      expect(instance.descontoPlanoContabil()).toEqual(expect.objectContaining({ id: 123 }));
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
