import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PerfilContadorAreaContabilDetailComponent } from './perfil-contador-area-contabil-detail.component';

describe('PerfilContadorAreaContabil Management Detail Component', () => {
  let comp: PerfilContadorAreaContabilDetailComponent;
  let fixture: ComponentFixture<PerfilContadorAreaContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilContadorAreaContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PerfilContadorAreaContabilDetailComponent,
              resolve: { perfilContadorAreaContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PerfilContadorAreaContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilContadorAreaContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load perfilContadorAreaContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PerfilContadorAreaContabilDetailComponent);

      // THEN
      expect(instance.perfilContadorAreaContabil()).toEqual(expect.objectContaining({ id: 123 }));
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
