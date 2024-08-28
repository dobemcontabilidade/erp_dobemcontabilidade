import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlanoAssinaturaContabilDetailComponent } from './plano-assinatura-contabil-detail.component';

describe('PlanoAssinaturaContabil Management Detail Component', () => {
  let comp: PlanoAssinaturaContabilDetailComponent;
  let fixture: ComponentFixture<PlanoAssinaturaContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanoAssinaturaContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PlanoAssinaturaContabilDetailComponent,
              resolve: { planoAssinaturaContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PlanoAssinaturaContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanoAssinaturaContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planoAssinaturaContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PlanoAssinaturaContabilDetailComponent);

      // THEN
      expect(instance.planoAssinaturaContabil()).toEqual(expect.objectContaining({ id: 123 }));
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
